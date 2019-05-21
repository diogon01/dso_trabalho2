export interface IPais {
  id?: number;
  nomePais?: string;
}

export class Pais implements IPais {
  constructor(public id?: number, public nomePais?: string) {}
}
