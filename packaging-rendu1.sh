#! /bin/bash

target="rendu1"
pref="./lab_1/_code"

[ -d "$target" ] || mkdir -v $target

steps=()
steps+="${pref}/step1 "
steps+="${pref}/step2 "
steps+="${pref}/step3 "
steps+="${pref}/step4 "
steps+="${pref}/step5 "

i=1
for item in $steps; do
    echo "Packaging $item as step$i"
    cp -r $item $target
    i=$(($i+1))
done

cp -r "./lab_1/reports" $target

file="ArduinoML-Duboc-Escot.tar.gz"
tar czvf $file  rendu1/reports rendu1/step* 1> archive.log
mv $file rendu1
echo ""
echo "Les logs de l'archive sont dans 'rendu1/archive.log'"; echo ""
echo "Voulez-vous effectuer le rendu de l'archive par mail ? [O/n] "
read line


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
    exit                 #   then exit script.
}

trap "exitfn" INT            # Set up SIGINT trap to call function.


if [ "$line" != "O" ]; then
    echo ""
    echo "Ok, envoi de l'archive à laure.gonnord@ens-lyon.fr ..."
    sleep 2s; echo ""
    echo "Rendu effectué !"
    sleep "5s"
    exitfn
fi

# if [ "$line" = "O" ]; then
#     echo "Vraiment ? [O/n]"
#     read line2
#     if [ "$line2" = "O" ]; then
#       echo "TODO: configurer mail"
#     fi
# fi
