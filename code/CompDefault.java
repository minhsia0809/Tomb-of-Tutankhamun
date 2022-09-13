public class CompDefault extends Agent
{
    @Override
    public boolean decision(GameMetaData data)
    {
        Game.sleep((int)(500 + Math.random() * 1500));

        return (Math.random() < 0.85);
    }
}
