#!/bin/sh

npm install --global smee-client

smee --url https://smee.io/9v46c6cbSy3TKZzV --path /github-webhook/ --port 3030 --target http://jenkins:8080/github-webhook/