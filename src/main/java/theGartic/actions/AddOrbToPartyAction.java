package theGartic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import theGartic.cards.DistractingFox;
import theGartic.relics.PartyRelic;
import theGartic.summons.AbstractSummonOrb;
import theGartic.summons.FireImpSummon;
import theGartic.util.OrbTargetArrow;
import theGartic.util.OrbTargetScreen;

import static theGartic.util.Wiz.adp;
import static theGartic.util.Wiz.applyToSelfTop;

public class AddOrbToPartyAction extends AbstractGameAction implements OrbTargetArrow.OrbTargetArrowSubscriber {
    private String tipString;

    public AddOrbToPartyAction(String tipString) {
        actionType = ActionType.SPECIAL;
        startDuration = duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update()
    {
        int summonCount = 0;
        AbstractSummonOrb lastOrb = null;
        if (duration == startDuration)
        {
            for (AbstractOrb orb : adp().orbs)
            {
                if (orb instanceof AbstractSummonOrb)
                {
                    summonCount++;
                    lastOrb = ((AbstractSummonOrb)orb);
                }
            }
            if (summonCount == 0)
            {
                isDone = true;
                return;
            }
            else if (summonCount == 1)
            {
                addToParty(lastOrb);
                isDone = true;
                return;
            }
            else
            {
                OrbTargetScreen.Inst.open(this, tipString);
                tickDuration();
            }

            if(OrbTargetScreen.Inst.isActive && !OrbTargetArrow.isActive)
                isDone = true;
        } else
            isDone = true;
    }

    @Override
    public void receiveTargetOrb(AbstractCreature source, AbstractSummonOrb orb) {
        addToParty(orb);
        isDone = true;
    }

    private void addToParty(AbstractSummonOrb summonOrb)
    {
        if(adp().hasRelic(PartyRelic.ID))
            ((PartyRelic)adp().getRelic(PartyRelic.ID)).addToParty(summonOrb);
    }

    @Override
    public void end() {
        isDone = true;
    }

    @Override
    public boolean isAcceptableTarget(AbstractSummonOrb orb) {
        return orb instanceof AbstractSummonOrb;
    }
}
