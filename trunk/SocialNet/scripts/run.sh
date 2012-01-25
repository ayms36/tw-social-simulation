java -cp '../libs/jade.jar:../bin'  jade.Boot -container GuiAgentName:social.agents.GuiAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container europa:social.agents.LocalAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container azja:social.agents.LocalAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container afryka:social.agents.LocalAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container fate0:social.agents.FateAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container fate1:social.agents.FateAgent &
java -cp '../libs/jade.jar:../bin'  jade.Boot -container fate2:social.agents.FateAgent

