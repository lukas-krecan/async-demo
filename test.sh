#!/bin/bash
echo "Warmup for 2 seconds"
echo "GET http://localhost:8081/$1" | vegeta -cpus=2 attack -duration=3s -rate=10 > /dev/null
echo "Test for 10 seconds"
echo "GET http://localhost:8081/$1" | vegeta -cpus=2 attack -duration=10s -rate=$2 > result.bin; vegeta plot result.* > out.html; open out.html
vegeta report result.bin
