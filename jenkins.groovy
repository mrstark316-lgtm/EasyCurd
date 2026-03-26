pipeline {
    agent any

    environment {
        DOCKER_USER = "ashifmkndr"
        BACKEND_IMAGE = "${DOCKER_USER}/backend:${BUILD_NUMBER}"
        FRONTEND_IMAGE = "${DOCKER_USER}/frontend:${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/mrstark316-lgtm/EasyCurd.git'
            }
        }

        stage('Build Backend') {
            steps {
                sh '''
                cd backend
                mvn clean package -DskipTests=true
                docker build --no-cache -t $BACKEND_IMAGE .
                '''
            }
        }

        stage('Build Frontend') {
            steps {
                sh '''
                cd frontend
                docker build --no-cache -t $FRONTEND_IMAGE .
                '''
            }
        }

        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh '''
                    echo $PASS | docker login -u $USER --password-stdin
                    docker push $BACKEND_IMAGE
                    docker push $FRONTEND_IMAGE
                    '''
                }
            }
        }

        stage('Deploy to EKS') {
            steps {
                sh '''
                # Update backend
                kubectl set image deployment/backend backend=$BACKEND_IMAGE

                # Update frontend
                kubectl set image deployment/frontend frontend=$FRONTEND_IMAGE
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                echo "===== VERIFYING RUNNING IMAGES ====="
                kubectl get pods
                kubectl describe pod $(kubectl get pod -l app=frontend -o jsonpath="{.items[0].metadata.name}") | grep Image
                '''
            }
        }
    }
}
