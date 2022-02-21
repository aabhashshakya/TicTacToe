package com.avas.firebase_tictactoewithimportantfirebasefeatures;

public class User {
    private String name;
    private String myID;
    private String email;

    private String opponentID;
    private String opponentEmail;
    private boolean currentlyPlaying;
    private boolean weSentAnInvite;
    private boolean gotRequest;

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    private String accepted;


    public String getOpponentEmail() {
        return opponentEmail;
    }

    public void setOpponentEmail(String opponentEmail) {
        this.opponentEmail = opponentEmail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Game getMyGame() {
        return myGame;
    }

    public void setMyGame(Game myGame) {
        this.myGame = myGame;
    }

    private Game myGame;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    public String getMyID() {
        return myID;
    }

    public boolean isGotRequest() {
        return gotRequest;
    }

    public void setGotRequest(boolean gotRequest) {
        this.gotRequest = gotRequest;
    }

    public void setMyID(String myID) {
        this.myID = myID;
    }


    public String getOpponentID() {
        return opponentID;
    }

    public void setOpponentID(String opponentID) {
        this.opponentID = opponentID;
    }

    public boolean isCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(boolean currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public boolean isWeSentAnInvite() {
        return weSentAnInvite;
    }

    public void setWeSentAnInvite(boolean weSentAnInvite) {
        this.weSentAnInvite = weSentAnInvite;
    }

    public User(){

    }

    public User(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.myID = id;
        opponentID = "";
        opponentEmail = "";
        accepted = "none";
        gotRequest = false;
        myGame = null;
        currentlyPlaying = false;
    }
}
