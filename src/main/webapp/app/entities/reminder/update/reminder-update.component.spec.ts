import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReminderService } from '../service/reminder.service';
import { IReminder, Reminder } from '../reminder.model';

import { ReminderUpdateComponent } from './reminder-update.component';

describe('Reminder Management Update Component', () => {
  let comp: ReminderUpdateComponent;
  let fixture: ComponentFixture<ReminderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reminderService: ReminderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReminderUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ReminderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReminderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reminderService = TestBed.inject(ReminderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const reminder: IReminder = { id: 456 };

      activatedRoute.data = of({ reminder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(reminder));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reminder>>();
      const reminder = { id: 123 };
      jest.spyOn(reminderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reminder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reminder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(reminderService.update).toHaveBeenCalledWith(reminder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reminder>>();
      const reminder = new Reminder();
      jest.spyOn(reminderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reminder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reminder }));
      saveSubject.complete();

      // THEN
      expect(reminderService.create).toHaveBeenCalledWith(reminder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reminder>>();
      const reminder = { id: 123 };
      jest.spyOn(reminderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reminder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reminderService.update).toHaveBeenCalledWith(reminder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
