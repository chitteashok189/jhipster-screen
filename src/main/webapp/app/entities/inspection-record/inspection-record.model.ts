import dayjs from 'dayjs/esm';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { ILot } from 'app/entities/lot/lot.model';

export interface IInspectionRecord {
  id?: number;
  gUID?: string | null;
  rawMaterialBatchNo?: number | null;
  productBatchNo?: number | null;
  rawMaterialBatchCode?: number | null;
  inputBatchNo?: number | null;
  inputBatchCode?: number | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  inspectionID?: IInspection | null;
  lotID?: ILot | null;
}

export class InspectionRecord implements IInspectionRecord {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public rawMaterialBatchNo?: number | null,
    public productBatchNo?: number | null,
    public rawMaterialBatchCode?: number | null,
    public inputBatchNo?: number | null,
    public inputBatchCode?: number | null,
    public attachmentsContentType?: string | null,
    public attachments?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public inspectionID?: IInspection | null,
    public lotID?: ILot | null
  ) {}
}

export function getInspectionRecordIdentifier(inspectionRecord: IInspectionRecord): number | undefined {
  return inspectionRecord.id;
}
