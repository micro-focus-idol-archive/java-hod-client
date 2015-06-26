#!/usr/bin/env ruby

require_relative 'ruby-client'

def create_text_index(token, index_name, index_flavor, proxy_host, proxy_port)
  json = post("/api/async/textindex/#{index_name}/v1", proxy_host, proxy_port, :params => {:flavor => index_flavor}, :token => token)

  job_id = json['jobID']

  # wait for completion
  get("/job/#{job_id}/result", proxy_host, proxy_port, :token => token)
end

def add_to_text_index(token, filename, index_name, proxy_host, proxy_port)
  json = post("/api/async/textindex/#{index_name}/document/v1", proxy_host, proxy_port, :params => {
          :json => IO.read(filename),
          :duplicate_mode => 'replace'
      }, :token => token)

  job_id = json['jobID']

  # wait for completion
  get("/job/#{job_id}/result", proxy_host, proxy_port, :token => token)
end

apikey = ARGV[0]
proxy_host = ARGV[1]
proxy_port = ARGV[2]

unless apikey
  abort("Usage: #{__FILE__} APIKEY [PROXY_HOST] [PROXY_PORT]")
end

token = get_token(apikey, proxy_host, proxy_port)

private_index_names = list_indexes(proxy_host, proxy_port, token)

# set up required indexes
required_indexes = get_required_indexes

required_indexes.reject{|index| private_index_names.include?(index[:name])}.each do |index|
  puts "Creating index with name #{index[:name]}"
  create_text_index(token, index[:name], index[:flavor], proxy_host, proxy_port)
end

add_to_text_index(token, File.expand_path('../documents.json', __FILE__), required_indexes[0][:name], proxy_host, proxy_port)
