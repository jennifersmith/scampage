set -e

aws iam get-user | grep ludumdares35



rm -rf published
mkdir published
cp index.html published/
cp scampage.js published/
cp -r assets published/assets
cp -r out published/
cp -r lib published/
aws s3 sync published s3://jen-scampage/
