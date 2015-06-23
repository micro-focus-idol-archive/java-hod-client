#!/bin/bash

if [[ ${TRAVIS_BRANCH} == 'master' || ${TRAVIS_BRANCH} == 'develop' ]]
then
  echo "Deploying Jar to Maven Central"
  mvn deploy -DskipTests --settings settings.xml -Prelease
else
  echo "Not deploying jar"
fi

if [[ ${TRAVIS_BRANCH} == 'master' ]]
then
  echo "Building Maven Site and deploying to GitHub pages"
  git config credential.helper "store --file=.git/credentials"
  echo "https://${GITHUB_TOKEN}:@github.com" > .git/credentials
  mvn site
  # mvn site used to do this, but now API rate limiting makes it a non starter
  cd target/site
  git init
  git remote add origin "${GITHUB_TOKEN}:@github.com/${TRAVIS_REPO_SLUG}"
  git add .
  git commit -m "Update GitHub Pages"
  git push --force --quiet origin gh-pages > /dev/null 2>&1
fi