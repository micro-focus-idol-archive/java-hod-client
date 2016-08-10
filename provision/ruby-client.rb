require 'net/http'
require 'json'

class HodException < Exception

  def initialize(json)
    @json = json
  end

  def json
    @json
  end

end

def resolve_endpoint(environment)
  endpoints = {
      'INTEGRATION' => 'https://api.int.havenondemand.com',
      'PREVIEW' => 'https://api.preview.havenondemand.com',
      'STAGING' => 'https://api.staging.havenondemand.com',
      'PRODUCTION' => 'https://api.havenondemand.com'
  }

  endpoints[environment]
end

def get(endpoint, api, proxy_host, proxy_port, options = {})
  make_request(endpoint, api, proxy_host, proxy_port, Net::HTTP::Get, options)
end

def post(endpoint, api, proxy_host, proxy_port, options = {})
  make_request(endpoint, api, proxy_host, proxy_port, Net::HTTP::Post, options)
end

def put(endpoint, api, proxy_host, proxy_port, options = {})
  make_request(endpoint, api, proxy_host, proxy_port, Net::HTTP::Put, options)
end

def delete(endpoint, api, proxy_host, proxy_port, options = {})
  make_request(endpoint, api, proxy_host, proxy_port, Net::HTTP::Delete, options)
end

def make_request(endpoint, api, proxy_host, proxy_port, request_method, options = {})
  uri = URI.parse("#{endpoint}/2#{api}")

  puts "Using uri: #{uri}"

  if options[:params]
    uri.query = URI.encode_www_form(options[:params])
  end

  Net::HTTP::Proxy(proxy_host, proxy_port).start(uri.host, uri.port, :use_ssl => true, :ssl_version => :TLSv1) do |http|
    request = request_method.new(uri.request_uri)

    if options[:apikey]
      request.add_field('apikey', options[:apikey])
    end

    if options[:token]
      request.add_field('token', options[:token])
    end

    if options[:body]
      request.body = options[:body]
    end

    response = http.request(request)

    json = JSON.parse(response.body)

    if !(response.kind_of? Net::HTTPSuccess) || (json['actions'] && json['actions'][0]['errors'])
      puts response.body
      raise HodException.new(json), "Error making request #{uri}"
    end

    json
  end
end

def get_token(endpoint, apikey, application, domain, proxy_host, proxy_port)
  json = post(endpoint, '/authenticate/application', proxy_host, proxy_port, :apikey => apikey, :body => "name=#{application}&domain=#{domain}&token_type=simple")

  token = json['token']

  "#{token['type']}:#{token['id']}:#{token['secret']}"
end

def get_required_indexes
  [
      {:name => 'java-iod-client-integration-tests', :flavor => 'explorer'},
      {:name => 'java-iod-client-integration-tests-query-manipulation', :flavor => 'querymanipulation'}
  ]
end

def list_indexes(endpoint, proxy_host, proxy_port, token)
  index_list = get(endpoint, '/api/sync/resource/v1', proxy_host, proxy_port, :token => token, :params => {:type => 'content'})
  index_list['private_resources'].map {|resource| resource['resource']}
end
