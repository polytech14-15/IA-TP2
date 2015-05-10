package agent.strategy;

import java.util.List;
import java.util.Random;

import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Etat;
import environnement.gridworld.ActionGridworld;
/**
 * Strategie qui renvoit une action aleatoire avec probabilite epsilon, une action gloutonne (qui suit la politique de l'agent) sinon
 * Cette classe a acces a un RLAgent par l'intermediaire de sa classe mere.
 * @author lmatignon
 *
 */
public class StrategyGreedy extends StrategyExploration{
    //VOTRE CODE

    Double epsilon;

    private Random rand=new Random();



    public StrategyGreedy(RLAgent agent,double epsilon) {
        super(agent);
        //VOTRE CODE
        this.epsilon = epsilon;
    }

    /**
     * @return action selectionnee par la strategie d'exploration
     */
    @Override
    public Action getAction(Etat _e) {
        //VOTRE CODE
        List<Action> actions = this.getAgent().getActionsLegales(_e);
        if (actions.size() == 1) {
            return actions.get(0);
        } else if (actions.size() > 0) {
            // Si action d'exploration
            if (rand.nextDouble() <= epsilon){
                return actions.get(rand.nextInt(actions.size()));
            } else {
                //Sinon action gloutonne
                actions = this.getAgent().getPolitique(_e);
                return actions.get(rand.nextInt(actions.size()));
            }
        } else {
            return ActionGridworld.NONE;
        }
    }



    public void setEpsilon(double epsilon) {
        //VOTRE CODE
        this.epsilon = epsilon;
    }



}
