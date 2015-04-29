package agent.rlagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import environnement.Action;
import environnement.Environnement;
import environnement.Etat;
import environnement.gridworld.ActionGridworld;
import environnement.gridworld.EtatGrille;
import java.util.Map;
/**
 * 
 * @author laetitiamatignon
 *
 */
public class QLearningAgent extends RLAgent{
	//VOTRE CODE
	
        Map<Etat, Map<Action,Double>> QValeur;
	
	/**
	 * 
	 * @param alpha
	 * @param gamma
	 * @param Environnement
	 */
	public QLearningAgent(double alpha, double gamma,
			Environnement _env) {
		super(alpha, gamma,_env);
		//VOTRE CODE
		QValeur = new HashMap<>();
                reset();
	}


	
	
	/**
	 * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
	 *  
	 *  renvoi liste vide si aucunes actions possibles dans l'etat 
	 */
	@Override
	public List<Action> getPolitique(Etat e) {
		//VOTRE CODE
		//...
		return null;
		
		
	}
	
	/**
	 * @return la valeur d'un etat
	 */
	@Override
	public double getValeur(Etat e) {
            //VOTRE CODE
            // c'est un max sur les action
            double max = - Double.MAX_VALUE;
            List <Action> actions = this.getActionsLegales(e);
            for (Action a : actions){
                if (max < this.getQValeur(e, a)){
                    max = this.getQValeur(e, a);
                }
            }
            return max;
	}

	/**
	 * 
	 * @param e
	 * @param a
	 * @return Q valeur du couple (e,a)
	 */
	@Override
	public double getQValeur(Etat e, Action a) {
		//VOTRE CODE
            // TODO verrifier contains sinon renvoyer 0
		return this.QValeur.get(e).get(a);
	}
	
	/**
	 * setter sur Q-valeur
	 */
	@Override
	public void setQValeur(Etat e, Action a, double d) {
		//VOTRE CODE
		this.QValeur.get(e).put(a, d);
                // TODO
		//mise a jour vmin et vmax pour affichage gradient de couleur
                Double t_min = Double.MAX_VALUE;
                Double t_max = -Double.MAX_VALUE;
		for (Etat etat : this.QValeur.keySet()){
                    for (Double n : this.QValeur.get(etat).values()){
                        t_min = Math.min(t_min, n);
                        t_max = Math.max(t_max, n);
                    }
                }
		this.notifyObs();
	}
	
	
	/**
	 *
	 * mise a jour de la Q-valeur du couple (e,a) apres chaque interaction <etat e,action a, etatsuivant esuivant, recompense reward>
	 * la mise a jour s'effectue lorsque l'agent est notifie par l'environnement apres avoir realise une action.
	 * @param e
	 * @param a
	 * @param esuivant
	 * @param reward
	 */
	@Override
	public void endStep(Etat e, Action a, Etat esuivant, double reward) {
		//VOTRE CODE
		// calcul se trouvant diapo 38 cours
            Double result = (1 - super.alpha) * this.QValeur.get(e).get(a) + super.alpha * (reward + super.gamma * this.getValeur(e));
            this.QValeur.get(e).put(a, result);
            
	}

	@Override
	public Action getAction(Etat e) {
		this.actionChoisie = this.stratExplorationCourante.getAction(e);
		return this.actionChoisie;
	}

	/**
	 * reinitialise les Q valeurs
	 */
	@Override
	public void reset() {
		this.episodeNb =0;
		//VOTRE CODE
		this.QValeur.clear();
	}



	


}
