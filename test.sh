#!/bin/bash
echo "Warmup for 3 seconds"
echo "GET http://localhost:8081/$1" | vegeta -cpus=2 attack -duration=3s -rate=50 > /dev/null
echo "Test for 7 seconds"
echo "GET http://localhost:8081/$1" | vegeta -cpus=2 attack -duration=7s -rate=$2 > result.bin; vegeta plot result.* > out.html; open out.html
vegeta report result.bin
