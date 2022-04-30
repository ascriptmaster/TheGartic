package theGartic.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGartic.actions.AddOrbToPartyAction;
import theGartic.actions.CheckForPartyAction;
import theGartic.powers.PandamoniumPower;

import static theGartic.GarticMod.makeID;
import static theGartic.util.Wiz.applyToSelf;

public class TamersCandy extends AbstractEasyCard {

    public final static String ID = makeID(TamersCandy.class.getSimpleName());
    private final static int COST = 3;

    public TamersCandy()
    {
        super(ID, COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new CheckForPartyAction());
        addToBot(new AddOrbToPartyAction(cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    public void upp()
    {
        upgradeBaseCost(0);
        isEthereal = false;
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
    }
}