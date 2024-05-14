# In order to measure the time to first response,
# - run this script
# - start the server application with date +"%T.%3N" && java -jar target/...
# - Compare the timestamp of the debug log message of GreetingResource with the initial timestamp on the server console
while [ "$(curl -s -o /dev/null -w ''%{http_code}'' localhost:8080/api/greeting)" != "200" ]; do sleep .00001; done