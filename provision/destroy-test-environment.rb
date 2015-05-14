#!/usr/bin/env ruby

require_relative 'ruby-client'

def delete_text_index(token, index_name, proxy_host, proxy_port)
  api = "/api/sync/textindex/#{index_name}/v1"
  json = delete(api, proxy_host, proxy_port, :token => token)

  # yes, I really did want to delete it.....
  confirm_code = json['confirm']
  puts "Confirm code: #{confirm_code}"
  delete(api, proxy_host, proxy_port, :token => token, :params => {:confirm => confirm_code})
end

apikey = ARGV[0]
proxy_host = ARGV[1]
proxy_port = ARGV[2]

unless apikey
  abort("Usage: #{__FILE__} <APIKEY>")
end

token = get_token(apikey, proxy_host, proxy_port)

private_index_names = list_indexes(proxy_host, proxy_port, token)

get_required_indexes.find_all{|index| private_index_names.include?(index[:name])}.each do |index|
  puts "Deleting index with name #{index[:name]}"
  begin
    delete_text_index(token, index[:name], proxy_host, proxy_port)
  rescue HodException => e
    if e.json['error'] == 8002
      # index name invalid bug, nothing we can do
      puts "Cannot delete index with name #{index[:name]} due to a bug"
    else
      raise e
    end
  end
end





