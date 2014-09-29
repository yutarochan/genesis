import math

stories = ['AAAmericanRevolution',
           'AfghanistanCivilWar',
           'AmericanCivilWar',
           'CambodiaVietnamInvasion',
           'ChadLibyanWar',
           'ChinaBorderWarWithIndia',
           'ChinaBorderWarWithUSSR',
           'ChinaInvasionOfTibet',
           'ChinaWarWithVietnam',
           'CongoCivilConflict',
           'CubaBayOfPigsInvasion',
           'CzechoslovakiaSovietInvasion',
           'NigerianCivilWar',
           'PersianGulfWar',
           'RomaniaCeausescu']

total_words = {}

story_words = {}

titles = []

for story in stories:
    f = open(story + ' plot.txt')

    title = None

    started = False
    for line in f:
        line = line.split(' ');
        line[-1] = line[-1].replace('\n','').replace('.','')
        if not started:
            if line[0] == 'Start':
                started = True
                for i in xrange(4, len(line)):
                    line[i] = line[i].lower()
                title = str(line[3:])
                title = title.replace('\'','')
                title = title.replace('"','')
                title = title.replace(',','')
                title = title.replace('[','').replace(']','')
                title = title.lower()
                title = title.capitalize()
                titles.append(title)
            continue
        
        newline = []
        for word in line:
            if len(word) != 0:
                newline.append(word)

        line = newline

        if title not in story_words:
            story_words[title] = {}
        for word in line:
            if word not in total_words:
                total_words[word] = 1
            else:
                total_words[word] += 1

            if word not in story_words[title]:
                story_words[title][word] = 1
            else:
                story_words[title][word] += 1
            
    
    f.close()

angles = {}
for i in xrange(len(titles)):
    story1 = titles[i]
    angles[story1] = {}
    for j in xrange(len(titles)):        
        story2 = titles[j]

        if story1 == story2:
            angles[story1][story2] = 1.0
            continue
        
        story1Vec = story_words[story1]
        story2Vec = story_words[story2]

        l1 = 0.0
        for word in story1Vec:
            l1 += math.pow(story1Vec[word], 2)
        l1 = math.sqrt(l1)

        l2 = 0.0
        for word in story2Vec:
            l2 += math.pow(story2Vec[word], 2)
        l2 = math.sqrt(l2)

        denom = l1 * l2

        #if (int)denom - (float)denom < .001:
        #    denom = denom + 0.5

        total = 0.0
        for word in story1Vec:
            val1 = story1Vec[word]

            val2 = 0.0
            if word in story2Vec:
                val2 = story2Vec[word]

            total += (val1 * val2) / denom

        angles[story1][story2] = total
        if story2 not in angles:
            angles[story2] = {}
        angles[story2][story1] = total
        
print 'HashMap<String, HashMap<String,Double>> keyword_angles = ' \
        + 'new HashMap<String, HashMap<String,Double>>();'
for story1 in titles:
    print 'keyword_angles.put("'+story1+'", new HashMap<String,Double>());'
for story1 in titles:
    for story2 in titles:
        print 'keyword_angles.get("'+story1+'").put("'+story2+'", ', \
              angles[story1][story2], ');'
                    


