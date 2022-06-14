<div id="top"></div>

# SQLITE-Editor

<p align="center">
<a href="https://github.com/RababElAmerany94/billing-management-mobile">
<img  alt="Rabab El Amerany Project" title="Rabab Project" src="https://github.com/RababElAmerany94/SQLITE-Editor/blob/master/mobile-app.PNG" />
</a>
</p>
<br>
<br>

<p align="left">
SqliteEditor is an Android application for SQLITE database management. Our goal is to create an application that will help users within a company; the goal is to provide them with an interface allowing basic manipulation of databases. The main functions of this application are: the creation of objects (databases, tables, lines), the deletion of objects (lines, tables) in addition to an authentication layer allowing to distinguish between two types of users (Administrator and normal user).

<b>How the app works</b>

Our application is based on internal databases allowing the storage of users, and tables created. The start interface includes a connection form allowing each user authentication to access the view dedicated to him. There are two types of view depending on the type of user: "Administrator" or "User".

The "Administrator" view allows the management and search of users with an account on the application, while the "User" view allows through an interface the creation of databases, tables, the execution of SQL queries and the database export.
</p>

## Built With

* [Android](https://www.android.com)
* [SQLite](https://www.sqlite.org)
* [Java](https://www.java.com)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple example steps.

## Pre-requisites

The application currently requires JDK  to build. If you already have JDK installed, skip this step.

**Check if the right JDK is already available**

Run the command `java -version`. If you have the JDK installed, you should see something like:

```sh
openjdk version "11.0.10" 2021-01-19
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.10+9)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.10+9, mixed mode)
```

If this command has an error, or shows a different version is not a problem, you can follow the instructions below to install the JDK.

**Install the JDK**

We recommend using [jEnv](https://www.jenv.be/) to manage your JDK installations. Here are instructions to setup a working JDK 1.8 installation :

1. Setup up [Homebrew](https://brew.sh/).

2. Install `jEnv` using Homebrew.

```sh
brew install jenv
```

3. Add the following lines to your shell configuration file (`~/.bash_profile` if you're using bash, or `~/.zshrc` if you're using zsh).

```sh
export PATH="$HOME/.jenv/bin:$PATH"
eval "$(jenv init -)"
```

4. Once this is done, you'll need to restart the terminal or reload the configuration file in order for the `jenv` command to be recognised.

```sh
source <path to shell configuration file>
```

5. Install the JDK using Homebrew.

```sh
brew tap AdoptOpenJDK/openjdk
brew install adoptopenjdk11
```

6. Add the installed JDK to `jEnv` or another version

```sh
jenv add /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home
```

7. Run the command `jenv versions`. You should see something like:

```sh
  system
  11
* 11.0
  11.0.10
  openjdk64-11.0.10
```

**Clone the project using git.**

Run the following command in a terminal.

 ```
 $ git clone [https://github.com/RababElAmerany94/SQLITE-Editor](https://github.com/RababElAmerany94/SQLITE-Editor)
 ```

<p align="right">(<a href="#top">back to top</a>)</p>

**Install Android Studio**

Download and install Android Studio from [their website](https://developer.android.com/studio/).

<p align="right">(<a href="#top">back to top</a>)</p>

**Import the project into Android Studio.**

When Android Studio starts up, it will prompt you to create a new project or import an existing project. Select the option to import an existing
project, navigate to the `SQLITE-Editor` directory you cloned earlier, and select it.

When building for the first time, gradle will download all dependencies so it'll take a few minutes to complete. Subsequent builds will be faster.

<p align="right">(<a href="#top">back to top</a>)</p>

## Running locally

The Simple App can be run locally on an Android emulator using Android Studio. To do this,

<p align="right">(<a href="#top">back to top</a>)</p>

**Create a Run/Debug configuration**

* Open the Run/Debug configurations window through Run -> Edit Configurations ([ref](https://developer.android.com/studio/run/rundebugconfig))
* Create a new configuration using the `Android App` template
* Set the module to `app`, and finish creating the configuration

<p align="right">(<a href="#top">back to top</a>)</p>

**Create a virtual device**

* Create an Android Virtual Device (AVD) using the AVD Manager, usually found in Tools -> AVD
  Manager. ([ref](https://developer.android.com/studio/run/managing-avds))
* Select a device and operating system
* Note: You will have to download one of the available OS options the first time you create an AVD

<p align="right">(<a href="#top">back to top</a>)</p>

**Set the right build variant**

* Open the Build Variants window through View -> Tool Windows -> Build Variants, or clicking the item in the lower left corner of the main window
* Set the Build Variant of the app module to `qaDebug`

<p align="right">(<a href="#top">back to top</a>)</p>

**Run the app**

* Click "Run", either through Run -> Run, or the green play button in the top toolbar.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

Rabab El Amerany - rab.el.amerany@gmail.com

Project Link: [https://github.com/RababElAmerany94/platform-to-track-orders](https://github.com/RababElAmerany94/platform-to-track-orders)

<p align="right">(<a href="#top">back to top</a>)</p>
