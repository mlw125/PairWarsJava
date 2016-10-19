package pairWars.view;

public class CardView {

	public String getCard(int suit, int face) {
		// this string is used to build up the card suit and rank to be displayed
		String card = "";
		
		// check the suit of the card
		switch(suit) {
		case 0:
			card += "\u2660";
			break;
		case 1:
			card += "\u2665";
			break;
		case 2:
			card += "\u2666";
			break;
		case 3:
			card += "\u2663";
			break;
		default:
			card += "E";
			break;
		} // end switch/case

		// check the face of the card
		switch(face) {
		case 1:
			card += "A";
			break;
		case 2:
			card += "2";
			break;
		case 3:
			card += "3";
			break;
		case 4:
			card += "4";
			break;
		case 5:
			card += "5";
			break;
		case 6:
			card += "6";
			break;
		case 7:
			card += "7";
			break;
		case 8:
			card += "8";
			break;
		case 9:
			card += "9";
			break;
		case 10:
			card += "10";
			break;
		case 11:
			card += "J";
			break;
		case 12:
			card += "Q";
			break;
		case 13:
			card += "K";
			break;
		default:
			card += "E";
			break;
		} // end switch/case
		
		return card;
	} // end getCard()
} // end class CardView
