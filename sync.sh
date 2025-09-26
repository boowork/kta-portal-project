rm -rf ../kta-portal/{docker,admin,portal,school,api}
cp -r docker ../kta-portal/docker
cp -r admin ../kta-portal/admin
cp -r portal ../kta-portal/portal
cp -r school ../kta-portal/school
cp -r api ../kta-portal/api
cp -r util ../kta-portal/util
cp .gitignore ../kta-portal/
mkdir -p ../kta-portal/docs/example/front
cp -r docs/example/front ../kta-portal/docs/example/front
rm ../kta-portal/admin/backend/devrule.md
rm ../kta-portal/admin/front/devrule.md
