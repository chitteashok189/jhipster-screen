import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ReminderService } from '../service/reminder.service';

import { ReminderComponent } from './reminder.component';

describe('Reminder Management Component', () => {
  let comp: ReminderComponent;
  let fixture: ComponentFixture<ReminderComponent>;
  let service: ReminderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReminderComponent],
    })
      .overrideTemplate(ReminderComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReminderComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReminderService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.reminders?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
