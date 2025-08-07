#!/usr/bin/env python3

from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse, parse_qs
import os

class QueryStringRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
         # Parse the URL path and query string
        parsed_path = urlparse(self.path)

        print("parsed_path:", parsed_path)

        query_components = parse_qs(parsed_path.query)
        lang = query_components.get("lang", [""])[0]  # Default to empty string if not provided

        print("lang:", lang)

        # Remove and save the extension from the file_name
        base_path = parsed_path.path.strip("/")
        base_name, ext = os.path.splitext(base_path)
        if not ext:
            ext = ".json"  # Default to .json if no extension

        # Concatenate the lang parameter with the last part of the path
        if lang:
            file_name = base_name + "_" + lang + ext
        else:
            file_name = base_name + ext

        print("file_name:", file_name)

        # Check if the file exists and load its content
        if os.path.isfile(file_name):
            with open(file_name, 'r') as file:
                file_content = file.read()
            print("file_content:", file_content)
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            self.wfile.write(file_content.encode("utf-8"))
        else:
            self.send_response(404)
            self.send_header("Content-type", "text/plain")
            self.end_headers()
            self.wfile.write(f"File '{file_name}' not found.".encode("utf-8"))
            
# Set up the server
def run(server_class=HTTPServer, handler_class=QueryStringRequestHandler, port=8000):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f"Starting httpd on port {port}...")
    httpd.serve_forever()

if __name__ == "__main__":
    run()
