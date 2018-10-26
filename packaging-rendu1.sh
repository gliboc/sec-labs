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
steps+="${pref}/step6 "
steps+="${pref}/step7"

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
cp ./lab_1/papiers.md $target
cp README.md $target

file="ArduinoML-Duboc-Escot.tar.gz"
tar czvf $file  rendu1 1> archive.log
mv $file rendu1
echo ""
echo "Les logs de l'archive sont dans 'rendu1/archive.log'"; echo ""

exitfn () {
    trap SIGINT              # Restore signal handling for SIGINT
    echo ""; echo "Oups ?"
    trap "lastfn" INT
    sleep "5s"
    lastfn
}

lastfn () {
    echo ""
    echo "Blague à part, l'archive est dans 'rendu1'."
    echo "Tu vas devoir l'envoyer à la main..."
    exit                 #   then exit script.
}

trap "exitfn" INT            # Set up SIGINT trap to call function.


if [ "$line" != "O" ]; then
    echo ""
    echo "Ok, envoi de l'archive à eddy.caron@ens-lyon.fr ..." 
    sleep 2s; echo ""
    echo "Rendu effectué !"
    sleep "7s"
    exitfn
fi

if [ "$line" = "O" ]; then
    echo "Vraiment ? [O/n]"
    read line2
    if [ "$line2" = "O" ]; then
      echo "TODO: configurer mail"
    fi
fi


