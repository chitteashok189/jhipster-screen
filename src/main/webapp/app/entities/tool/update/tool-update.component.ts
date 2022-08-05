import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITool, Tool } from '../tool.model';
import { ToolService } from '../service/tool.service';
import { ToolType } from 'app/entities/enumerations/tool-type.model';

@Component({
  selector: 'jhi-tool-update',
  templateUrl: './tool-update.component.html',
})
export class ToolUpdateComponent implements OnInit {
  isSaving = false;
  toolTypeValues = Object.keys(ToolType);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    toolType: [],
    toolName: [],
    manufacturer: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected toolService: ToolService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tool }) => {
      if (tool.id === undefined) {
        const today = dayjs().startOf('day');
        tool.createdOn = today;
        tool.updatedOn = today;
      }

      this.updateForm(tool);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tool = this.createFromForm();
    if (tool.id !== undefined) {
      this.subscribeToSaveResponse(this.toolService.update(tool));
    } else {
      this.subscribeToSaveResponse(this.toolService.create(tool));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITool>>): void {
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

  protected updateForm(tool: ITool): void {
    this.editForm.patchValue({
      id: tool.id,
      gUID: tool.gUID,
      toolType: tool.toolType,
      toolName: tool.toolName,
      manufacturer: tool.manufacturer,
      createdBy: tool.createdBy,
      createdOn: tool.createdOn ? tool.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: tool.updatedBy,
      updatedOn: tool.updatedOn ? tool.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITool {
    return {
      ...new Tool(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      toolType: this.editForm.get(['toolType'])!.value,
      toolName: this.editForm.get(['toolName'])!.value,
      manufacturer: this.editForm.get(['manufacturer'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
