#!/bin/bash

if [[ $TRAVIS_BRANCH == 'master' || $TRAVIS_BRANCH == 'develop' ]]
then
  mvn deploy -DskipTests --settings settings.xml -Prelease
fi

if [[ $TRAVIS_BRANCH == 'master' ]]
then
  git config credential.helper "store --file=.git/credentials"
  echo "https://${GITHUB_TOKEN}:@github.com" > .git/credentials
  mvn site -Prelease
fi