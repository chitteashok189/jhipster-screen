import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SecurityGroupPermissionService } from '../service/security-group-permission.service';

import { SecurityGroupPermissionComponent } from './security-group-permission.component';

describe('SecurityGroupPermission Management Component', () => {
  let comp: SecurityGroupPermissionComponent;
  let fixture: ComponentFixture<SecurityGroupPermissionComponent>;
  let service: SecurityGroupPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SecurityGroupPermissionComponent],
    })
      .overrideTemplate(SecurityGroupPermissionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityGroupPermissionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SecurityGroupPermissionService);

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
    expect(comp.securityGroupPermissions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
