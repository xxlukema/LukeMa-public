
files=$(find src -name *.spec.ts)

for i in ${files}
do
    echo ============= ${i} =============
    # ng test --watch=false --include ${i}
    ng test --watch=false --browsers=ChromeHeadless --include ${i}
done
