#!/usr/bin/env bash
sudo -u postgres createuser -P companies
sudo -u postgres createdb -O companies companies
sudo -u postgres createuser -P companies_test
sudo -u postgres createdb -O companies_test companies_test
