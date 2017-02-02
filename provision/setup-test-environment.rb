#!/usr/bin/env ruby

require_relative 'ruby-client'

def create_text_index(endpoint, token, index_name, index_flavor, proxy_host, proxy_port)
  json = post(endpoint, "/api/async/textindex/#{index_name}/v2", proxy_host, proxy_port, :params => {:flavor => index_flavor}, :token => token)

  job_id = json['jobID']

  # wait for completion
  get(endpoint, "/job/#{job_id}/result", proxy_host, proxy_port, :token => token)
end

def add_to_text_index(endpoint, token, filename, index_name, domain, proxy_host, proxy_port)
  json = post(endpoint, "/api/async/textindex/#{domain}:#{index_name}/document/v1", proxy_host, proxy_port, :params => {
          :json => IO.read(filename),
          :duplicate_mode => 'replace'
      }, :token => token)

  job_id = json['jobID']

  # wait for completion
  get(endpoint, "/job/#{job_id}/result", proxy_host, proxy_port, :token => token)
end

apikey = ARGV[0]
environment = ARGV[1]
application = ARGV[2]
domain = ARGV[3]
proxy_host = ARGV[4]
proxy_port = ARGV[5]

endpoint = resolve_endpoint(environment)

unless endpoint && apikey && application && domain
  abort("Usage: #{__FILE__} APIKEY ENVIRONMENT APPLICATION DOMAIN [PROXY_HOST] [PROXY_PORT]")
end

puts "Using #{endpoint}"

token = get_token(endpoint, apikey, application, domain, proxy_host, proxy_port)

private_index_names = list_indexes(endpoint, domain, proxy_host, proxy_port, token)

# set up required indexes
required_indexes = get_required_indexes

required_indexes.reject{|index| private_index_names.include?(index[:name])}.each do |index|
  puts "Creating index with name #{index[:name]}"
  create_text_index(endpoint, token, index[:name], index[:flavor], proxy_host, proxy_port)
end

add_to_text_index(endpoint, token, File.expand_path('../documents.json', __FILE__), required_indexes[0][:name], domain, proxy_host, proxy_port)
