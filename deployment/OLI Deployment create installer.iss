; -- Example1.iss --
; Demonstrates copying 3 files and creating an icon.

; SEE THE DOCUMENTATION FOR DETAILS ON CREATING .ISS SCRIPT FILES!

[Setup]
AppName=OLI
AppVersion=1.0
DefaultDirName={pf}\Towson Univeristy
DefaultGroupName=OLI
UninstallDisplayIcon={app}\uninstallOLI.exe
Compression=lzma2
SolidCompression=yes
OutputDir=userdocs:Inno Setup Examples Output

[Files]
Source: "OLI.exe"; DestDir: "{app}";
Source: "lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs;
Source: "mysql-installer-web-community-5.6.23.0.msi"; DestDir: {tmp}; Flags: deleteafterinstall;
Source: "Oli_Icon.ico"; DestDir: "{app}";
       
[Icons]
Name: "{group}\Object Lab Interface"; IconFilename: {app}\Oli_Icon.ico; Filename: "{app}\OLI.EXE"; WorkingDir: "{app}"; Comment: "Object Lab Interface";

[Code]
var
  MyProgChecked: Boolean;
  MyProgCheckResult: Boolean;
  FinishedInstall: Boolean;


procedure CurStepChanged(CurStep: TSetupStep);
begin
  Log('CurStepChanged(' + IntToStr(Ord(CurStep)) + ') called');
  if CurStep = ssPostInstall then
    FinishedInstall := True;
end;

procedure CurInstallProgressChanged(CurProgress, MaxProgress: Integer);
begin
  Log('CurInstallProgressChanged(' + IntToStr(CurProgress) + ', ' + IntToStr(MaxProgress) + ') called');
end;

function NextButtonClick(CurPageID: Integer): Boolean;
var
  ResultCode: Integer;
var
  ErrorCode: Integer;
begin
  Log('NextButtonClick(' + IntToStr(CurPageID) + ') called');
  case CurPageID of
    wpSelectDir: ;
      //MsgBox('NextButtonClick:' #13#13 'You selected: ''' + WizardDirValue + '''.', mbInformation, MB_OK);
    wpSelectProgramGroup:  ;
      //MsgBox('NextButtonClick:' #13#13 'You selected: ''' + WizardGroupValue + '''.', mbInformation, MB_OK);
    wpReady:
      begin
        if MsgBox('Would you like to install the database on this machine?' #13#13, mbConfirmation, MB_YESNO) = idYes then begin
                    ExtractTemporaryFile('mysql-installer-web-community-5.6.23.0.msi');              
                                  ShellExec('', 'msiexec',
  ExpandConstant('/I "{tmp}\mysql-installer-web-community-5.6.23.0.msi" /qb'),
  '', SW_SHOWNORMAL, ewWaitUntilTerminated, ErrorCode);

        BringToFrontAndRestore();
        //MsgBox('NextButtonClick:' #13#13 'The normal installation will now start.', mbInformation, MB_OK);
            end;
      end;
  end;

  Result := True;
end;

function BackButtonClick(CurPageID: Integer): Boolean;
begin
  Log('BackButtonClick(' + IntToStr(CurPageID) + ') called');
  Result := True;
end;

function ShouldSkipPage(PageID: Integer): Boolean;
begin
  Log('ShouldSkipPage(' + IntToStr(PageID) + ') called');
  { Skip wpInfoBefore page; show all others }
  case PageID of
    wpInfoBefore:
      Result := True;
  else
    Result := False;
  end;
end;

function MyProgCheck(): Boolean;
begin
  Log('MyProgCheck() called');
  if not MyProgChecked then begin
    //MyProgCheckResult := MsgBox('MyProgCheck:' #13#13 'Using the script you can decide at runtime to include or exclude files from the installation. Do you want to install MyProg.exe and MyProg.chm to ' + ExtractFilePath(CurrentFileName) + '?', mbConfirmation, MB_YESNO) = idYes;
    //MyProgChecked := True;
  end;
  Result := MyProgCheckResult;
end;


function MyConst(Param: String): String;
begin
  Log('MyConst(''' + Param + ''') called');
  Result := ExpandConstant('{pf}');
end;