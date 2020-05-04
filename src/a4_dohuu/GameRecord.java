/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a4_dohuu;

/**
 * This class models a class representing a board game or video game
 * @author Khang Do, 2020
 */
public class GameRecord {
    private String gameName;
    private int minNumPlayer;
    private int maxNumPlayer;
    private double price;
    private String description;
    
    /**
     * Construct a no-arg GameRecord
     */
    public GameRecord(){        
        
    }
    
    /**
     * Construct a game record
     * @param gameName
     * @param minNumPlayer
     * @param maxNumPlayer
     * @param price
     * @param description 
     */
    public GameRecord(String gameName, int minNumPlayer, int maxNumPlayer,
            double price, String description){
        setGameName(gameName);
        setMinNumPlayer(minNumPlayer);
        setMaxNumPlayer(maxNumPlayer);
        setPrice(price);
        setDescription(description);        
    }

    /**
     * Gets the game name
     * @return the gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the game name
     * @param gameName the gameName to set
     * @throws IllegalArgumentException if the game name is null or empty string
     */
    public void setGameName(String gameName) throws IllegalArgumentException{
        if ((gameName != null) && !(gameName.trim().equals(""))){
        this.gameName = gameName;
    } else throw new IllegalArgumentException("Game name can not be "
            + "null or empty!");
    }

    /**
     * Gets the minimum number of players
     * @return the minNumPlayer
     */
    public int getMinNumPlayer() {
        return minNumPlayer;
    }

    /**
     * @param minNumPlayer the minNumPlayer to set
     * @throws IllegalArgumentException if the minimum number is not between 1 
     * 4 inclusive
     */
    public void setMinNumPlayer(int minNumPlayer) throws 
            IllegalArgumentException{
        if ((minNumPlayer >= 1) && (minNumPlayer <= 4)){
        this.minNumPlayer = minNumPlayer;
        } else throw new IllegalArgumentException("Minimum number of players "
                + "is between 1 and 4 inclusive.");
    }

    /**
     * Gets the maximum number of players
     * @return the maxNumPlayer
     */
    public int getMaxNumPlayer() {
        return maxNumPlayer;
    }

    /**
     * Sets the maximum number of players of a game
     * @param maxNumPlayer the maxNumPlayer to set
     * @throws IllegalArgumentException if the maximum number is not in range of 
     * minimum number and 10 inclusive
     */
    public void setMaxNumPlayer(int maxNumPlayer) throws 
            IllegalArgumentException{
        if ((maxNumPlayer >= minNumPlayer) && (maxNumPlayer <= 10)){
        this.maxNumPlayer = maxNumPlayer;
        } else throw new IllegalArgumentException("Maximum number of players "
                + "must be in range of " + minNumPlayer + " and 10 inclusive");
    }

    /**
     * Gets the price for a game
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price for a game
     * @param price the price to set
     * throws IllegalArgumentException if price is negative
     */
    public void setPrice(double price) throws IllegalArgumentException{
        if (price >= 0){
        this.price = price;
        } else throw new IllegalArgumentException("Price can not be negative!");
    }

    /**
     * Gets a description of Game Record
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a description of Game Record.
     * @param description the description to set
     * @throws IllegalArgumentException if description is null or empty string
     */
    public void setDescription(String description) throws 
            IllegalArgumentException{
        if ((description != null) && !(description.trim().equals(""))){
        this.description = description;
        } else throw new IllegalArgumentException("Description can not be "
                + "null or empty!");
    }
    
    /**
     * Displays message for GameRecord
     * @return a message with game name, minimum and maximum number of Players
     * and its description
     */
    @Override
    public String toString(){
        return String.format("%s is for %d to %d players, %s\n", 
                gameName, minNumPlayer, maxNumPlayer, description);        
    }
        
//    public String fromString(){
//        return gameName;
//    }
    
    /**
     * Compares 2 GameRecords
     * @param o Object that compared
     * @return true if they have the same if they have the same game name,
     * minimum number of players and maximum number of players
     */
    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (this == o){
            return true;           
        }
        
        if (o instanceof GameRecord){
            GameRecord g = (GameRecord) o;
            if(this.getGameName() == g.getGameName() && 
                    this.getMinNumPlayer() == g.getMinNumPlayer()
                    && this.getMaxNumPlayer() == g.getMaxNumPlayer()){
                return true;
            } 
        } 
        return false;
        
    }
    
}
