folder('seeds')
folder('configs')
folder('pipelines')

buildMonitorView('pipelines/monitor') {
    description('All jobs for project A')
    jobs {
        name('All')
        regex(/pipelines.+/)
    }
}

dashboardView('pipelines/dashboard') {
    jobs {
        regex(/pipelines/.*/)
    }
    columns {
        status()
        weather()
        buildButton()
    }
    topPortlets {
        jenkinsJobsList {
            displayName('jobs')
        }
    }
    leftPortlets {
        testStatisticsChart()
    }
    rightPortlets {
        testTrendChart()
    }
    bottomPortlets {
        iframe {
            effectiveUrl('http://example.com')
        }
        testStatisticsGrid()
        buildStatistics()
    }
}