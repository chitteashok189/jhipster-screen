import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICalendar, Calendar } from '../calendar.model';
import { CalendarService } from '../service/calendar.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { IZone } from 'app/entities/zone/zone.model';
import { ZoneService } from 'app/entities/zone/service/zone.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ITool } from 'app/entities/tool/tool.model';
import { ToolService } from 'app/entities/tool/service/tool.service';
import { ISeason } from 'app/entities/season/season.model';
import { SeasonService } from 'app/entities/season/service/season.service';

@Component({
  selector: 'jhi-calendar-update',
  templateUrl: './calendar-update.component.html',
})
export class CalendarUpdateComponent implements OnInit {
  isSaving = false;

  plantFactoriesSharedCollection: IPlantFactory[] = [];
  zonesSharedCollection: IZone[] = [];
  cropsSharedCollection: ICrop[] = [];
  toolsSharedCollection: ITool[] = [];
  seasonsSharedCollection: ISeason[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    calenderDate: [],
    calenderYear: [],
    dayOfWeek: [],
    monthOfYear: [],
    weekOfMonth: [],
    weekOfQuarter: [],
    weekOfYear: [],
    dayOfQuarter: [],
    dayOfYear: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    plantFactoryID: [],
    zoneID: [],
    cropID: [],
    toolID: [],
    seasonID: [],
  });

  constructor(
    protected calendarService: CalendarService,
    protected plantFactoryService: PlantFactoryService,
    protected zoneService: ZoneService,
    protected cropService: CropService,
    protected toolService: ToolService,
    protected seasonService: SeasonService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendar }) => {
      if (calendar.id === undefined) {
        const today = dayjs().startOf('day');
        calendar.calenderDate = today;
        calendar.createdOn = today;
        calendar.updatedOn = today;
      }

      this.updateForm(calendar);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calendar = this.createFromForm();
    if (calendar.id !== undefined) {
      this.subscribeToSaveResponse(this.calendarService.update(calendar));
    } else {
      this.subscribeToSaveResponse(this.calendarService.create(calendar));
    }
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  trackZoneById(_index: number, item: IZone): number {
    return item.id!;
  }

  trackCropById(_index: number, item: ICrop): number {
    return item.id!;
  }

  trackToolById(_index: number, item: ITool): number {
    return item.id!;
  }

  trackSeasonById(_index: number, item: ISeason): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendar>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(calendar: ICalendar): void {
    this.editForm.patchValue({
      id: calendar.id,
      gUID: calendar.gUID,
      calenderDate: calendar.calenderDate ? calendar.calenderDate.format(DATE_TIME_FORMAT) : null,
      calenderYear: calendar.calenderYear,
      dayOfWeek: calendar.dayOfWeek,
      monthOfYear: calendar.monthOfYear,
      weekOfMonth: calendar.weekOfMonth,
      weekOfQuarter: calendar.weekOfQuarter,
      weekOfYear: calendar.weekOfYear,
      dayOfQuarter: calendar.dayOfQuarter,
      dayOfYear: calendar.dayOfYear,
      createdBy: calendar.createdBy,
      createdOn: calendar.createdOn ? calendar.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: calendar.updatedBy,
      updatedOn: calendar.updatedOn ? calendar.updatedOn.format(DATE_TIME_FORMAT) : null,
      plantFactoryID: calendar.plantFactoryID,
      zoneID: calendar.zoneID,
      cropID: calendar.cropID,
      toolID: calendar.toolID,
      seasonID: calendar.seasonID,
    });

    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      calendar.plantFactoryID
    );
    this.zonesSharedCollection = this.zoneService.addZoneToCollectionIfMissing(this.zonesSharedCollection, calendar.zoneID);
    this.cropsSharedCollection = this.cropService.addCropToCollectionIfMissing(this.cropsSharedCollection, calendar.cropID);
    this.toolsSharedCollection = this.toolService.addToolToCollectionIfMissing(this.toolsSharedCollection, calendar.toolID);
    this.seasonsSharedCollection = this.seasonService.addSeasonToCollectionIfMissing(this.seasonsSharedCollection, calendar.seasonID);
  }

  protected loadRelationshipsOptions(): void {
    this.plantFactoryService
      .query()
      .pipe(map((res: HttpResponse<IPlantFactory[]>) => res.body ?? []))
      .pipe(
        map((plantFactories: IPlantFactory[]) =>
          this.plantFactoryService.addPlantFactoryToCollectionIfMissing(plantFactories, this.editForm.get('plantFactoryID')!.value)
        )
      )
      .subscribe((plantFactories: IPlantFactory[]) => (this.plantFactoriesSharedCollection = plantFactories));

    this.zoneService
      .query()
      .pipe(map((res: HttpResponse<IZone[]>) => res.body ?? []))
      .pipe(map((zones: IZone[]) => this.zoneService.addZoneToCollectionIfMissing(zones, this.editForm.get('zoneID')!.value)))
      .subscribe((zones: IZone[]) => (this.zonesSharedCollection = zones));

    this.cropService
      .query()
      .pipe(map((res: HttpResponse<ICrop[]>) => res.body ?? []))
      .pipe(map((crops: ICrop[]) => this.cropService.addCropToCollectionIfMissing(crops, this.editForm.get('cropID')!.value)))
      .subscribe((crops: ICrop[]) => (this.cropsSharedCollection = crops));

    this.toolService
      .query()
      .pipe(map((res: HttpResponse<ITool[]>) => res.body ?? []))
      .pipe(map((tools: ITool[]) => this.toolService.addToolToCollectionIfMissing(tools, this.editForm.get('toolID')!.value)))
      .subscribe((tools: ITool[]) => (this.toolsSharedCollection = tools));

    this.seasonService
      .query()
      .pipe(map((res: HttpResponse<ISeason[]>) => res.body ?? []))
      .pipe(map((seasons: ISeason[]) => this.seasonService.addSeasonToCollectionIfMissing(seasons, this.editForm.get('seasonID')!.value)))
      .subscribe((seasons: ISeason[]) => (this.seasonsSharedCollection = seasons));
  }

  protected createFromForm(): ICalendar {
    return {
      ...new Calendar(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      calenderDate: this.editForm.get(['calenderDate'])!.value
        ? dayjs(this.editForm.get(['calenderDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      calenderYear: this.editForm.get(['calenderYear'])!.value,
      dayOfWeek: this.editForm.get(['dayOfWeek'])!.value,
      monthOfYear: this.editForm.get(['monthOfYear'])!.value,
      weekOfMonth: this.editForm.get(['weekOfMonth'])!.value,
      weekOfQuarter: this.editForm.get(['weekOfQuarter'])!.value,
      weekOfYear: this.editForm.get(['weekOfYear'])!.value,
      dayOfQuarter: this.editForm.get(['dayOfQuarter'])!.value,
      dayOfYear: this.editForm.get(['dayOfYear'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
      zoneID: this.editForm.get(['zoneID'])!.value,
      cropID: this.editForm.get(['cropID'])!.value,
      toolID: this.editForm.get(['toolID'])!.value,
      seasonID: this.editForm.get(['seasonID'])!.value,
    };
  }
}
