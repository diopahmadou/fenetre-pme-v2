import { IPme } from 'app/shared/model//pme.model';

export interface IProjet {
    id?: number;
    investissementMateriel?: string;
    fondRoulement?: number;
    organismesFinancement?: string;
    pme?: IPme;
}

export class Projet implements IProjet {
    constructor(
        public id?: number,
        public investissementMateriel?: string,
        public fondRoulement?: number,
        public organismesFinancement?: string,
        public pme?: IPme
    ) {}
}
