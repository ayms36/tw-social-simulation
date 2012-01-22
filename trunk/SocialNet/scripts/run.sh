java -cp '../libs/jade.jar:../bin'  jade.Boot -container PresonDBAID:social.agents.PersonDB  &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container europa:social.agents.LocalAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container fate:social.agents.FateAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container MediaAgent:social.agents.MediaAgent



