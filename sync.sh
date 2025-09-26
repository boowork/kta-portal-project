rm -rf ../kta-portal/{docker,admin,portal,school,api}
cp -r docker ../kta-portal/docker
cp -r admin ../kta-portal/admin
cp -r api ../kta-portal/api
cp -r util ../kta-portal/util
cp .gitignore ../kta-portal/
mkdir -p ../kta-portal/docs/example/frontend
cp -r docs/example/frontend ../kta-portal/docs/example/frontend
rm ../kta-portal/admin/backend/devrule.md
rm ../kta-portal/admin/frontend/devrule.md
