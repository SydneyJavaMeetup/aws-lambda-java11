echo "Node12"
k6 run --out influxdb=http://localhost:8086/myk6db k6-node12.js
sleep 30
echo "Java8"
k6 run --out influxdb=http://localhost:8086/myk6db k6-java8.js
sleep 30
echo "Java11"
k6 run --out influxdb=http://localhost:8086/myk6db k6-java11.js
