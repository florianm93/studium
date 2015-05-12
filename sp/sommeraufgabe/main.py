"""
    Python Sommeraufgabe von Florian Macherey
    Datum 14.07.2014

    GroPro: Frequenzverteilung

    Hinweis: Der Dateiname der einzulesenden Datei muss als Option beim Aufruf angegeben werden. Die Daten werden dann in Dateiname.out geschrieben.
    Dies ist sicherer als Dateiname.Output.alteEndung, da bei Dateiname mit mehreren Punkten nicht eindeutig ist, welcher Teil zur Dateiendung gehoert (z.B. .vimrc, irgendwas.tar.gz,...)
    Im Ordner tests/ sind einige Testbeispiele mit Originalen Ausgabe Dateien.

"""
import math
import sys
import os.path as ispath

class Sender:
    """
        Klasse zur Implementierung eines Senders:

        Parameters:
        `self.x` : x-Koordinate
        `self.y` : y-Koordinate
        `self.radius` : Senderradius
    """
    def __init__(self, x, y, radius, sid):
        self.x = x
        self.y = y
        self.radius = radius
        self.sid = sid
        self.frequenz = 0
        self.gesperrte_frequenzen = []

    def __str__(self):
        #return "S%02d: %7.3f, %7.3f, %7.3f" % (self.sid+1, self.x, self.y, self.radius)
        return "S%02d" % (self.sid+1)

def give_freq(choosen_sender, setted_step3):
    """
        Verteilen einer Frequenz und setzen der gesperrten Frequenzen der umliegenden Sender

        **Parameters:**

        `choosen_sender`:
            Sender der eine Frequenz zugeteilt bekommt.
        `setted_freq`:
            Variable die *True* wird, wenn der erste Sender gesetzt ist. Dann muss zuerst Fall 3 bearbeitet werden.
    """
    freq = 1
    setted_freq = False

    for s in sender_liste:
        # Setzten der naechsten Freien Frequenz
        if s.sid == choosen_sender.sid:
            while s.frequenz == 0:
                if freq not in s.gesperrte_frequenzen:
                    s.frequenz = freq
                    setted_freq =  True
                    setted_step3 = True
                else:
                    freq += 1

        # Wenn die Frequenz gesetzt wurde, diese entsprechend bei den anderen Sendern die sich schneiden sperren.
        if setted_freq:
            for s2 in sender_liste:
                if schnitt_mit_sender[choosen_sender.sid][s2.sid] == True and freq not in sender_liste[s2.sid].gesperrte_frequenzen:
                    sender_liste[s2.sid].gesperrte_frequenzen.append(freq)

    temp_string = str(choosen_sender)+"->"+str(choosen_sender.frequenz)
    if temp_string not in zugeteilte_sender:
        zugeteilte_sender.append(temp_string)

    return True



def verteile_Frequenzen(anzahl_schnitte, meiste_schnitte, sender_schnitte):
    """
        Funktion zur bestimmung welcher Sender als naechstes eine Frequenz zugeteilt werden soll.

        **Parameters:**

        `anzahl_schnitte`:
            Liste mit allen Anzahlen von Schnitten der Sender. Posistion i in der Liste: Sender i hat *anzahl_schnitte[i]* Schnitte mit anderen Sendern. *0* bedeutet, dass der Sender bereits bearbeitet wurde.
        `meiste_schnitte`:
            Anzahl der Schnitte eines Senders mit anderen Sendern
        `sender_schnitte`:
            Koordinaten der Sender mit der Anzahl an Schnitten aus *meiste_schnitte*
        `setted_step3`:
            Variable die *True* wird, wenn der erste Sender gesetzt ist. Dann muss zuerst Fall 3 bearbeitet werden.
    """
    global setted_step3
    print sender_schnitte

    #s_temp = sender_liste[0]
    #if setted_step3:
    #    # Fall 3: Sender mit den meisten gesperrten Frequenzen waehlen
    #    for s in sender_liste[1:]:
    #        if len(s.gesperrte_frequenzen) > len(s_temp.gesperrte_frequenzen):
    #            s_temp = s
    #            anzahl_schnitte[s_temp.sid] = 0
    #    setted_step3 = give_freq(s_temp, setted_step3)

    if len(sender_schnitte) == 1:
        # Fall 1.1: Es gibt nur einen Sender der die meisten Schnitte hat. D.h. groesste freie Frequenz vergeben und bei den Schneidenden Sendern sperren.
        setted_step3 = give_freq(sender_liste[sender_schnitte[0]], setted_step3)
    else:
        while sender_schnitte != []:

            s_temp = sender_liste[sender_schnitte[0]]
            index = 0

            for i, s in enumerate(sender_schnitte[1:]):
                if sender_liste[s].x < s_temp.x:
                    # Fall 1.2, westlichster Sender
                    s_temp = sender_liste[s]
                    index = i+1
                elif sender_liste[s].x == s_temp.x and sender_liste[s].y < s_temp.y:
                    # Fall 1.3, suedlichster Sender
                    s_temp = sender_liste[s]
                    index = i+1
            sender_schnitte.pop(index)
            setted_step3 = give_freq(s_temp, setted_step3)


        return setted_step3
### MAIN ###

sender_liste = []
schnitt_mit_sender = []
zugeteilte_sender = []
global setted_step3
setted_step3 = False

# Einlesen der Sender und Speichern in einer Liste sowie berechenen der Abstaende
filename = ""
if len(sys.argv) != 2:
    print "[err ] usage: python main.py <filename>"
    sys.exit(1)        #quit programm with exit code 1
else:
    filename = sys.argv[1]

try:
    with open(filename, "r") as f:

        # Daten fuer Ausgabedatei festlegen und entsprechend Ausgeben
        print "**\n** Daten aus Datei: "+filename+"\n**\n\n Senderpositionen (X,Y) und Senderradien:"
        lines = f.readlines()
        sender_id = 0
        for l in lines:
            if l[:2] == "**":
                continue        # Kommentar in Datei, Zeile wird uebersprungen
            else:
                tmp_list = l.split()
                s_new = Sender(float(tmp_list[0]), float(tmp_list[1]), float(tmp_list[2]), int(sender_id))

                print " %s: %7.3f, %7.3f, %7.3f" % (s_new, s_new.x, s_new.y, s_new.radius)

                sender_liste.append(s_new)
                schnitt_mit_sender.append([])

                # Abstand von jedem Sender zu jedem bestimmen. Sender die sich mit anderen schneiden in Liste speichern. Dabei ist die erste Dimension der Index des Senders, die zweite sind die Abstaende des Senders zu anderen
                for s1 in sender_liste:

                    abstand = math.sqrt(math.pow((s1.x-s_new.x), 2) + math.pow((s1.y-s_new.y), 2))

                    id1 = s1.sid
                    id_new = s_new.sid

                    # Pruefen ob sich der Sender mit den anderen schneidet: None==Sender schneidet sich trivial mit sich selbst, True==Sender schneidet sich mit anderem Sender, False==Kein Schnitt
                    if id1 == id_new:
                        schnitt_mit_sender[id1].append(None)
                    elif abstand <= (s1.radius+s_new.radius):
                        schnitt_mit_sender[id1].append(True)
                        schnitt_mit_sender[id_new].append(True)
                    else:
                        schnitt_mit_sender[id1].append(False)
                        schnitt_mit_sender[id_new].append(False)

                sender_id += 1
except (IOError):
    print "[err ] Die Datei "+str(filename)+" existiert scheinbar nicht!"
    sys.exit(1)

# Bestimmen der Anzahl der Ueberschneidungen mit Sender i
anzahl_schnitte = [0]*len(schnitt_mit_sender)

for index, schnitt in enumerate(schnitt_mit_sender):
    for s in schnitt:
        if s:
            anzahl_schnitte[index] += 1

# Bestimmen der Sender mit den meisten Schnitten mit anderen Sendern: meiste_schritte = Anzahl Schnitte, mehrere_schnitte = Index in "meiste_schnitte"
meiste_schnitte = 0
mehrere_schnitte = []
is_ready = False


while not is_ready:
    if setted_step3:
        s_temp = sender_liste[0]
        for (i,s) in enumerate(sender_liste[1:]):
            if len(s.gesperrte_frequenzen) > len(s_temp.gesperrte_frequenzen):
                s_temp = sender_liste[i+1]
                meiste_schnitte = anzahl_schnitte[i+1]
                mehrere_schnitte = [i+1]

        #setted_step3 = give_freq(s_temp, setted_step3)


    else:
        for i, a in enumerate(anzahl_schnitte):
            if a > meiste_schnitte:
                meiste_schnitte = a
                mehrere_schnitte = [i]

            elif a == meiste_schnitte:
                if i not in mehrere_schnitte:
                    mehrere_schnitte.append(i)

    for p in range(len(mehrere_schnitte)-1, -1, -1):
        # Nullen fuer bereits bearbeitete Sender eintragen
        anzahl_schnitte[mehrere_schnitte[p]] = None

    if meiste_schnitte == 0:
        break

    # Abfragen ob alle Sender bereits bearbeitet wurden
    for i in mehrere_schnitte:
        if i == None:
            is_ready = True
        else:
            is_ready = False
            break

    # Frequenzen bestimmen
    setted_step3 = verteile_Frequenzen(anzahl_schnitte, meiste_schnitte, mehrere_schnitte)

    meiste_schnitte = 0
    mehrere_schnitte = []

print "\n Frequenzzuordnung:"

for z in zugeteilte_sender:
    print " "+z

# Bestimmen der maximalen Frequenz die benutzt wird
max_freq = 1
for s in sender_liste:
    if s.frequenz > max_freq:
        max_freq = s.frequenz

# Sender entsprechend in Listen schreiben um einfacher auf die Ergebnisse zugreifen zu koennen
sender_pro_freq = [[] for _ in range(max_freq+1)]
for s in sender_liste:
    sender_pro_freq[s.frequenz].append(str(s))

# Tabelle in Datei schreiben
print "\n Anzahl benoetiger Frequenzen: "+str(max_freq)+"\n Frequenz | Sender\n "+(87*"=")

for (i,s) in enumerate(sender_pro_freq):
    if s == []:
        continue
    else:
        temp_string = "    "+str(i)+"     |  "
        for j in s:
            temp_string += (str(j)+"  ")
        print temp_string

# Programmende
