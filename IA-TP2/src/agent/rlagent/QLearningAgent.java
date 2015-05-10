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
            List<Action> listActions = new ArrayList<>();
            Double max = this.getValeur(e);
            for ( Action a : this.getActionsLegales(e)){
                if (this.getQValeur(e, a) == max){
                    listActions.add(a);
                }
            }
            return listActions;
	}
	
	/**
	 * @return la valeur d'un etat
	 */
	@Override
	public double getValeur(Etat e) {
            //VOTRE CODE
            // c'est un max sur les actions
            double max = - Double.MAX_VALUE;
            for (Action a : this.getActionsLegales(e)){
                max = Math.max(max, this.getQValeur(e, a));
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
		return this.QValeur.containsKey(e) && this.QValeur.get(e).containsKey(a) ? this.QValeur.get(e).get(a) : 0.0;
	}
	
	/**
	 * setter sur Q-valeur
	 */
	@Override
	public void setQValeur(Etat e, Action a, double d) {
            //VOTRE CODE
            if (this.QValeur.containsKey(e)){
                this.QValeur.get(e).put(a, d);
            } else {
                Map<Action, Double> map = new HashMap<>();
                map.put(a, d);
                this.QValeur.put(e, map);
            }
            
            //mise a jour vmin et vmax pour affichage gradient de couleur
            Double t_min = Double.MAX_VALUE;
            Double t_max = -Double.MAX_VALUE;
            for (Etat etat : this.QValeur.keySet()){
                for (Double n : this.QValeur.get(etat).values()){
                    t_min = Math.min(t_min, n);
                    t_max = Math.max(t_max, n);
                }
            }

            super.vmax = t_max;
            super.vmin = t_min;
        
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
            if (a != ActionGridworld.EXIT){
                Double result = (1 - super.alpha) * this.getQValeur(e, a) + super.alpha * (reward + super.gamma * this.getValeur(esuivant));
                this.setQValeur(e, a, result);
            }
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
            super.vmax = - Double.MAX_VALUE;
            super.vmin = Double.MAX_VALUE;
	}



	


}
