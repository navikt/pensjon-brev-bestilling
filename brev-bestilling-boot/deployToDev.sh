#!/bin/sh
gcloud auth login
gcloud functions deploy brev-bestilling-boot --region europe-west1 --entry-point org.springframework.cloud.function.adapter.gcp.GcfJarLauncher --project pensjonsbrev-dev-2fb6 --runtime java11 --trigger-topic my-functions-topic --source target/deploy --memory 512MB