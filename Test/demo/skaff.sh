echo "####### Starting Ephemeral Cloud #########"

namespace=$1
profile=$2

#check Argument
if [ $# -ne 2 ]; then
    echo "Required arguments provided"
    echo " Pass Namespace and Profile"
    exit 1
fi

#check profile 
if [ "$profile" = "edp-dev " ]; then
   aws eks --region us-west-2 update-kubeconfig --name dev-nightvision-EksCluster-1WFJO4DI4RB9U-EksControlPlane
else
   aws eks --region us-west-2 update-kubeconfig --name prod-nightvision-EksCluster-1MW1QP5LGG5YB-EksControlPlane
fi

#create Namespace
kubectl create ns ${namespace}

if [ $? -ne 0 ]
then
   echo "Namespace not created "
else   
   echo "Namespace ${namespace} created in Profile $profile "
fi

#Fetch Gcr json key for authentication from Pod
#kubectl get secret gcr-json-key --namespace nightvision-dev  --export -o yaml | kubectl apply --namespace=$namespace -f- >> /dev/null 

echo " starting Skaffold Deplyoments "
#skaffold dev --default-repo=us.gcr.io/spwr-cd-90fd/nightvision -p $profile -n ${namespace}
skaffold build --default-repo=us.gcr.io/pruthvi12 -p $profile -n ${namespace} --file-output=inginx.json

echo "Deleting namespace"
#kubectl delete ns $namespace

echo "deleting all helm charts and helm releases"

#delete all helm charts and helm releases
#helm ls --all --namespace ${namespace} --short | xargs -L1 helm delete --namespace ${namespace}
#helm repo list  | xargs -L1 helm repo remove 

# Delete Namespace
delete ns $namespace
