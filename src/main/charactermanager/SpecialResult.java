package main.charactermanager;

/**
 * Carries the full outcome of a house special ability so the UI layer
 * can animate and log it without knowing any house-specific logic.
 */
public class SpecialResult {

    /** Button label, e.g. "DRAGONFIRE". */
    public final String name;

    /** Opening line logged before the hit animations play. */
    public final String callout;

    /**
     * Damage dealt per hit. Index 0 = first hit, index 1 = second (Stark only).
     * 0 means the hit missed.
     */
    public final int[] damages;

    /** Whether each hit missed (parallel to damages[]). */
    public final boolean[] missed;

    /**
     * Complete log line per hit when it connects (damage already embedded).
     * Parallel to damages[].
     */
    public final String[] hitLogs;

    /**
     * Complete log line per hit when it misses.
     * Parallel to damages[].
     */
    public final String[] missLogs;

    /**
     * HP to restore to the caster after all hits (0 = none).
     * BattleScreen applies the maxHp cap before setting HP.
     */
    public final int healing;

    /**
     * Optional separate log line shown when healing is applied.
     * Null if healing is already described in a hit/miss log.
     */
    public final String healLog;

    public SpecialResult(String name, String callout,
                         int[] damages, boolean[] missed,
                         String[] hitLogs, String[] missLogs,
                         int healing, String healLog) {
        this.name     = name;
        this.callout  = callout;
        this.damages  = damages;
        this.missed   = missed;
        this.hitLogs  = hitLogs;
        this.missLogs = missLogs;
        this.healing  = healing;
        this.healLog  = healLog;
    }
}
