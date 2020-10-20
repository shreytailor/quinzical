<h1 align="center">Quinzical with JavaFX 🇳🇿</h1>

> To see the documentation 📝 of the overall development process, visit the Wiki page [here](./wiki/home.md). It contains all the notes regarding the design decisions, meeting notes, and everything else.

<hr>

## Usage Instructions
1. Make sure to firstly test this on the latest SE206 VirtualBox Image to have the required dependencies, and also install the voices provided by Catherine to use the New Zealand accent.

2. To get started, first install **festival** and **festlex-oald** text-to-speech dependencies using the commands below.

```
sudo apt-get install festival
sudo apt-get install festlex-oald
```
3. Place the `.jar` file, `run_game.sh` script, and `Quinzical` file which contains the questions, into a new directory of your choice.

4. Open the terminal and change the current working directory to the directory created above.

5.  To run the game, give executable permissions to the script file.

```
chmod +x run_game.sh
```

6. Run the game using this command. Please do note that this script is tailored for the second version of the SE206 image, given that you have installed the voices provided by Catherine.

```
./run_game.sh
```

## Attributions

**Pacifico Font**\
Under [Open Font Licence](https://scripts.sil.org/cms/scripts/page.php?site_id=nrsi&id=OFL) - can be freely used in commercial products.\
[It can be found here.](https://fonts.google.com/specimen/Pacifico)

**Poppins Font**\
Under [Open Font Licence](https://scripts.sil.org/cms/scripts/page.php?site_id=nrsi&id=OFL) - can be freely used in commercial products.\
[It can be found here.](https://fonts.google.com/specimen/Poppins)

**Background Image**
Under [Freepik Licence](https://www.freepikcompany.com/legal#nav-freepik-license) - free for personal use and commercial purpose with attribution.
[It can be found here.](https://www.freepik.com/free-vector/welcome-new-zealand-landing-page_6345340.htm#page=1&query=new%20zealand&position=4)

<img width="20px" src="./src/a3/quinzical/frontend/resources/icons/speaker.png">\
Icon of the "Speaker" provided by Pixel Perfect.\
Under Free Licence (With Attribution).\
[It can be found here.](https://www.flaticon.com/free-icon/volume_727269?term=speaker&page=1&position=1)

<img width="20px" src="./src/a3/quinzical/frontend/resources/icons/back.png">\
Icon of the "Back Arrow" provided by Google.\
Under Free Licence (With Attribution).\
[It can be found here.](https://www.flaticon.com/free-icon/back-arrow_566095?term=back%20arrow&page=1&position=36)

<img width="20px" src="./src/a3/quinzical/frontend/resources/icons/random.png">\
Icon of the "Dice" provided by Good Ware.\
Under Free Licence (With Attribution).\
[It can be found here.](https://www.flaticon.com/free-icon/random_2619060?term=random&page=1&position=6)