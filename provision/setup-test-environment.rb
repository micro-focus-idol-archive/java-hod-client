#!/usr/bin/env ruby

require_relative 'ruby-client'

def create_text_index(token, index_name, index_flavor, proxy_host, proxy_port)
  json = post("/api/async/textindex/#{index_name}/v1", proxy_host, proxy_port, :params => {:flavor => index_flavor}, :token => token)

  job_id = json['jobID']

  # wait for completion
  get("/job/#{job_id}/result", proxy_host, proxy_port, :token => token)
end

apikey = ARGV[0]
proxy_host = ARGV[1]
proxy_port = ARGV[2]

unless apikey
  abort("Usage: #{__FILE__} <APIKEY>")
end

token = get_token(apikey, proxy_host, proxy_port)

private_index_names = list_indexes(proxy_host, proxy_port, token)

# set up required indexes
get_required_indexes.reject{|index| private_index_names.include?(index[:name])}.each do |index|
  puts "Creating index with name #{index[:name]}"
  create_text_index(token, index[:name], index[:flavor], proxy_host, proxy_port)
end









