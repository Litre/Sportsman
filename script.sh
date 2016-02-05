
#!/bin/bash

#defining if weekend

if [[ $(date +%u) -gt 5]]; then
is_weekend=1
else
is_weekend=0
fi


#copying logo

if [[ is_weekend -eq 1 ]]; then
cp ./Weekend/logo.jpg ./Target
else
cp ./BusinessDay/logo.jpg ./Target
fi