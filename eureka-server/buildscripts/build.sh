#Nevicate to  the current directory
echo "navigating to your directory"
cd "${BASH_SOURCE%/*}" || exit
cd ..
export SONAR_TOKEN=715fc0eac466bc9b5e551b4fc695894e02543492
echo "sonar token is : $SONAR_TOKEN"

echo 'Running jacoco:prepare-agent...'
mvn clean jacoco:prepare-agent install
echo 'Running jacoco:report...'
mvn jacoco:report
echo 'Running sonar:sonar...'
mvn sonar:sonar \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.organization=iamsaransh \
  -Dsonar.projectKey=naming-server