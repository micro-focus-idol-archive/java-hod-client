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

def get(api, proxy_host, proxy_port, options = {})
  make_request(api, proxy_host, proxy_port, Net::HTTP::Get, options)
end

def post(api, proxy_host, proxy_port, options = {})
  make_request(api, proxy_host, proxy_port, Net::HTTP::Post, options)
  end

def put(api, proxy_host, proxy_port, options = {})
  make_request(api, proxy_host, proxy_port, Net::HTTP::Put, options)
end

def delete(api, proxy_host, proxy_port, options = {})
  make_request(api, proxy_host, proxy_port, Net::HTTP::Delete, options)
end

def make_request(api, proxy_host, proxy_port, request_method, options = {})
  uri = URI.parse("https://api.dev.idolondemand.com/2#{api}")

  if options[:params]
    uri.query = URI.encode_www_form(options[:params])
  end

  Net::HTTP::Proxy(proxy_host, proxy_port).start(uri.host, uri.port, :use_ssl => true) do |http|
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

    unless response.kind_of? Net::HTTPSuccess
      puts response.body
      raise HodException.new(json), "Error making request #{uri}"
    end

    json
  end
end

def get_token(apikey, proxy_host, proxy_port)
  json = post('/authenticate/application', proxy_host, proxy_port, :apikey => apikey, :body => 'name=IOD-TEST-APPLICATION&domain=IOD-TEST-DOMAIN&token_type=simple')

  token = json['token']

  "#{token['type']}:#{token['id']}:#{token['secret']}"
end

def get_required_indexes
  [
      {:name => 'java-iod-client-integration-tests', :flavor => 'explorer'},
      {:name => 'java-iod-client-integration-tests-query-manipulation', :flavor => 'querymanipulation'}
  ]
end

def list_indexes(proxy_host, proxy_port, token)
  index_list = get("/api/sync/listindexes/v1", proxy_host, proxy_port, :token => token, :params => {:type => 'content'})
  index_list['index'].map {|index| index['index']}
end
