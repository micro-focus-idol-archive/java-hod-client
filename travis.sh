#!/bin/bash

if [[ $TRAVIS_BRANCH == 'master' ]]
then
  mvn site -Prelease
fi