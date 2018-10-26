#! /bin/bash

target="rendu1"
pref="./lab_1/_code"

rm -rf $target

[ -d "$target" ] || mkdir -v $target

steps=()
steps+="${pref}/step1 "
steps+="${pref}/step2 "
steps+="${pref}/step3 "
steps+="${pref}/step4 "
steps+="${pref}/step5 "
steps+="${pref}/step6"

mkdir $target/reports

i=1
for item in $steps; do
    echo "Packaging $item as step$i"
    cp -r $item $target
    cp $item/report$i.md $target/reports
    pandoc $item/report$i.md -o $item/report$i.pdf
    i=$(($i+1))
done

pandoc $target/reports/*.md -o $target/reports.pdf
cat $target/reports/*.md > $target/reports.md
cp $target/reports.{md,pdf} ./lab_1

file="ArduinoML-Duboc-Escot.tar.gz"
tar czvf $file  rendu1/reports rendu1/step* 1> archive.log
mv $file rendu1
echo ""
echo "Les logs de l'archive sont dans 'rendu1/archive.log'"; echo ""


