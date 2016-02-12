package bb.api.algorithms;

import bb.api.domain.League;
import bb.api.domain.Match;
import bb.api.domain.MatchDay;
import bb.api.domain.Team;

import java.util.List;

public class BergerTablesMatchSchedulingAlgorithm implements
        MatchSchedulingAlgorithm {

    @Override
    public void schedule(League league) {
        //from http://it.wikipedia.org/wiki/Algoritmo_di_Berger#Java
        List<Team> teams = league.teams;
        int numberOfTeams = teams.size();

        int days = numberOfTeams - 1;
        if (league.hasSecondLeg) {
            days = (numberOfTeams - 1) * 2;
        }
     
	    /* crea gli array per le due liste in casa e fuori */
        Team[] home = new Team[numberOfTeams / 2];
        Team[] away = new Team[numberOfTeams / 2];

        for (int i = 0; i < numberOfTeams / 2; i++) {
            home[i] = teams.get(i);
            away[i] = teams.get(numberOfTeams - 1 - i);
        }
        List<MatchDay> allMatches = league.matchDays;
        for (int i = 0; i < days; i++) {
            MatchDay dayMatches = new MatchDay();
            /* stampa le partite di questa giornata */
            //System.out.printf("%d^ Giornata\n",i+1);
	 
	        /* alterna le partite in casa e fuori */
            if (i % 2 == 0) {
                for (int j = 0; j < numberOfTeams / 2; j++) {
                    // System.out.printf("%d  %s - %s\n", j+1, away[j].name, home[j].name); 
                    //add the match to result
                    dayMatches.matches.add(new Match(away[j], home[j]));
                }

            } else {
                for (int j = 0; j < numberOfTeams / 2; j++) {
                    // System.out.printf("%d  %s - %s\n", j+1, home[j].name, away[j].name); 
                    //add the match to result
                    dayMatches.matches.add(new Match(home[j], away[j]));
                }
            }
            //allMatches[i]=dayMatches;
            allMatches.add(dayMatches);

            // Ruota in gli elementi delle liste, tenendo fisso il primo elemento
            // Salva l'elemento fisso
            Team pivot = home[0];
	 
	        /* sposta in avanti gli elementi di "trasferta" inserendo 
	           all'inizio l'elemento casa[1] e salva l'elemento uscente in "riporto" */
            Team riporto = shiftRight(away, home[1]); 
	 
	        /* sposta a sinistra gli elementi di "casa" inserendo all'ultimo 
	           posto l'elemento "riporto" */
            shiftLeft(home, riporto);

            // ripristina l'elemento fisso
            home[0] = pivot;
        }

    }


    private void shiftLeft(Team[] casa, Team riporto) {
        for (int i = 0; i < casa.length - 1; i++) {
            casa[i] = casa[i + 1];
        }
        casa[casa.length - 1] = riporto;
    }


    private Team shiftRight(Team[] trasferta, Team primo) {
        Team riporto = trasferta[trasferta.length - 1];
        for (int i = trasferta.length - 1; i > 0; i--) {
            trasferta[i] = trasferta[i - 1];
        }
        trasferta[0] = primo;
        return riporto;
    }

}
