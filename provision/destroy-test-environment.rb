#!/usr/bin/env ruby

require_relative 'ruby-client'

def delete_text_index(endpoint, token, index_name, domain, proxy_host, proxy_port)
  api = "/api/sync/textindex/#{domain}:#{index_name}/v1"
  json = delete(endpoint, api, proxy_host, proxy_port, :token => token)

  # yes, I really did want to delete it.....
  confirm_code = json['confirm']
  puts "Confirm code: #{confirm_code}"
  delete(endpoint, api, proxy_host, proxy_port, :token => token, :params => {:confirm => confirm_code})
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

get_required_indexes.find_all{|index| private_index_names.include?(index[:name])}.each do |index|
  puts "Deleting index with name #{index[:name]}"
  begin
    delete_text_index(endpoint, token, index[:name], domain, proxy_host, proxy_port)
  rescue HodException => e
    if e.json['error'] == 8002
      # index name invalid bug, nothing we can do
      puts "Cannot delete index with name #{index[:name]} due to a bug"
    else
      raise e
    end
  end
end
