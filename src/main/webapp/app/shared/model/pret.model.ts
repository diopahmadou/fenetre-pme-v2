import { IFichier } from 'app/shared/model//fichier.model';
import { IPme } from 'app/shared/model//pme.model';

export const enum EtatPret {
    ACCORD = 'ACCORD',
    REFUS = 'REFUS',
    ATTENTE = 'ATTENTE'
}

export interface IPret {
    id?: number;
    montant?: number;
    etat?: EtatPret;
    taux?: number;
    echeancier?: number;
    garanties?: string;
    fichiers?: IFichier[];
    pme?: IPme;
}

export class Pret implements IPret {
    constructor(
        public id?: number,
        public montant?: number,
        public etat?: EtatPret,
        public taux?: number,
        public echeancier?: number,
        public garanties?: string,
        public fichiers?: IFichier[],
        public pme?: IPme
    ) {}
}
